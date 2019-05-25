package blueKite.com.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import blueKite.com.entity.UserEntity;

@Mapper
public interface UserDao {
	
	@Select("select  id,username,password,phone,email,created,updated,openId from mb_user where id =#{userId}")
	UserEntity findById(@Param("userId") Integer userId);

	@Insert("INSERT  INTO mb_user (username,password,phone,email,created,updated) VALUES (#{username},#{password},#{phone},#{email},#{created},#{updated})")
	Integer insertUser(UserEntity userEntity);

	@Select("select  id,username,password,phone,email,created,updated,openId from mb_user where username =#{username} and password =#{password}")
	UserEntity login(@Param("username") String username, @Param("password") String password);

	@Select("select  id,username,password,phone,email,created,updated,openId from mb_user where openId =#{openId}")
	UserEntity findByOpenId(@Param("openId") String openId);
	
	@Update("update mb_user set openId = #{openId} where id = #{id}")
	Integer updateOpenId(UserEntity userEntity);
	
}
