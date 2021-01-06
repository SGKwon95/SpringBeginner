package com.example.study.service;

import com.example.study.ifs.CRUDInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService implements CRUDInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    // 1. request data 가져오기
    // 2. user 생성
    // 3. 생성된 데이터를 UserApiResponse에 넘긴다.
   @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

       // 1번
        UserApiRequest userApiRequest = request.getData();

        // 2번
       User user = User.builder()
               .account(userApiRequest.getAccount())
               .password(userApiRequest.getPassword())
               .status(UserStatus.REGISTERED)
               .phoneNumber(userApiRequest.getPhoneNumber())
               .email(userApiRequest.getEmail())
               .registeredAt(LocalDateTime.now())
               .build();
       User newUser = userRepository.save(user);

       // 3번

       return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
       //id->repository getOne, getById

        return userRepository.findById(id)
                .map(user->response(user))
                //.map(userApiResponse -> Header.OK(userApiResponse)) // 아래와 기능은 동일
                .map(Header::OK)
                .orElseGet(//찾는 유저가 없으면 이게 실행된다.
                        ()->Header.ERROR("No data")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
       // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
            // 3. update
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        })
        .map(user->userRepository.save(user)) // update
        .map(this::response) // userApiResponse
                .map(Header::OK)
        .orElseGet(()->Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long id) {
       // 1. id -> repository -> user

        Optional<User> optional = userRepository.findById(id);

       // 2. repository -> delete
        return optional.map(user->{
            userRepository.delete(user);
            return Header.OK();
        })
        .orElseGet(()->Header.ERROR("No data"));

       // 3. response return
    }

    private UserApiResponse response(User user) {
       // user -> userApiResponse Return
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())//나중에 암호화 작업 필요
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return
        return userApiResponse;
    }

    public Header<List<UserApiResponse>> search(Pageable pageable) {
       Page<User> users = userRepository.findAll(pageable);

       List<UserApiResponse> userApiResponseList = users.stream()
               .map(user -> response(user))
               .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();
        //Page를 그대로 return하면 좋겠지만 그러면 user에 password 들어있어서 안됨

       return Header.OK(userApiResponseList,pagination);
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

       //1. user찾기
        User user = userRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        //2. 찾은 user의 orderGroup 찾기
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    // 3. 해당 orderGroup의 item 가져오기기
                   List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }
}
