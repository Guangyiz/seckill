package edu.hust.se.seckill;

import edu.hust.se.seckill.domain.User;
import edu.hust.se.seckill.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUser(){
		User user = userService.getById(1);
		System.out.println(user);

	}

}
