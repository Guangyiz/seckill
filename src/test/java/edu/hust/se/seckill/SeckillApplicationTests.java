package edu.hust.se.seckill;

import edu.hust.se.seckill.domain.User;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.redis.UserKey;
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

	@Autowired
	private RedisService redisService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUser(){
		User user = userService.getById(1);
		System.out.println(user);

	}

	@Test
	public void testRedis(){
		User user = new User();
		user.setId(1);
		user.setName("zgy");
//		System.out.println(redisService.set(UserKey.getById,"key1",user));



		System.out.println(redisService.get(UserKey.getById,"key1",User.class));
	}

}
