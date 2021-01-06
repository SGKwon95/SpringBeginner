package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

//@Data 오류나면 build.gradle에 annotationProcessor 'org.projectlombok:lombok' 추가한다.
public class UserRepositoryTest extends StudyApplicationTests {
    @Autowired//DI,Dependency Injection
    //의존성 주입(new를 사용하지 않고 spring boot가 되면 자동으로 주입(생성)된다.)
    private UserRepository userRepository;

    @Test
    public void create(){
        /*
        User user = new User();
        user.setAccount("TestUser01");
        user.setEmail("TestUserEmail");
        user.setPhoneNumber("010-1111-1111");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("admin");

        User newUser = userRepository.save(user);
        System.out.println("newUser : "+newUser);
         */

        String account = "Test03";
        String password = "Test03";
        UserStatus status = UserStatus.REGISTERED;
        String email = "Test01@naver.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        /*
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";
         */

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        //기존의 생성자는 반드시 멤버변수 순서대로 넣어야 하는데 이럴 경우 나중에 멤버변수가 추가되면 오류가 발생할 수 있다.
        //또한 모든 멤버변수를 반드시 채워넣어야 하며, 그 중 몇개만 넣을 수 없다.
        //이 단점을 보완하기 위해서 builder를 사용한다.
        //해당 entity에 @Builder를 붙이면 생성자 대신 사용 가능하다.
        //User user2 = User.builder().account("").password("").status("").build();


        User newUser = userRepository.save(user);

        Assert.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        /*
        //Optional<User> user = userRepository.findById(3L);        //UserTable의 id=2인 user의 정보를 가져온다.
        Optional<User> user = userRepository.findByAccount("TestUser01");

        user.ifPresent(selectedUser ->{
            System.out.println("user : "+selectedUser);
        });


        //선택한 user가 어떤 아이템을 구매했는지 알아보기
        user.ifPresent(selectedUser ->{
            //여기서 stream()은 iterator와 역할이 비슷하다.
            selectedUser.getOrderDetailList().stream().forEach(detail ->{
                Item item = detail.getItem(); //있어도 되고 없어도 된다.
                System.out.println(item);
            });
        });
        */



        /*
        if(user != null) {
            ...
        }
        이런식으로 코드를 짜거나 또는 위의 User를 Optional<User>로 바꿔준다.
        이 Optional 은 null 값이 발생하지 않는다

        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("수령인 : " +orderGroup.getRevName());
            System.out.println("수령지 : " +orderGroup.getRevAddress());
            System.out.println("총 금액 : " +orderGroup.getTotalPrice());
            System.out.println("총 수량 : " +orderGroup.getTotalQuantity());
        });
        */


        Optional<User> targetUser = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-0001");

        targetUser.ifPresent(user->{
            //stream() 있어도 되고 없어도 된다.
            // stream()은 흐름이라는 뜻으로, stream()뒤에 여러가지 함수들을 붙일 수 있어서 사용한다.
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("------------주문 묶음-------------");
                System.out.println("수령인 : " +orderGroup.getRevName());
                System.out.println("수령지 : " +orderGroup.getRevAddress());
                System.out.println("총 금액 : " +orderGroup.getTotalPrice());
                System.out.println("총 수량 : " +orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("------------주문 상세-------------");
                    System.out.println("주문 상품 : " +orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " +orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 : " +orderDetail.getStatus());
                    System.out.println("도착예정일자 : " +orderDetail.getArrivalDate());
                    System.out.println("--------------------------------");
                });
                System.out.println();
            });
        });

        Assert.assertNotNull(targetUser);
    }

    @Test
    @Transactional//update문이 실행되고 업데이트된 컬럼을 마지막에 다시 롤백시킨다.
    public void update(){
        Optional<User> user = userRepository.findById(2L);        //UserTable의 id=2인 user의 정보를 가져온다.

        user.ifPresent(selectedUser ->{
            selectedUser.setAccount("PPPP");
            selectedUser.setUpdatedAt(LocalDateTime.now());
            selectedUser.setUpdatedBy("update method()");

            userRepository.save(selectedUser);
        });
    }

    @Test
    @Transactional//query문이 실행되고 삭제된 컬럼을 마지막에 다시 롤백시킨다.
    public void delete(){
        Optional<User> user = userRepository.findById(1L);

        Assert.assertTrue(user.isPresent());
        user.ifPresent(selectedUser->{
            userRepository.delete(selectedUser);
        });

        Optional<User> deletedUser = userRepository.findById(1L);
        Assert.assertFalse(deletedUser.isPresent());
    }

}
