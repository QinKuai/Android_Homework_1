package edu.bjtu.android.dao;

import edu.bjtu.android.entity.ElearnUser;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElearnUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElearnUser record);

    ElearnUser selectByPrimaryKey(Integer id);

    List<ElearnUser> selectAll();

    int updateByPrimaryKey(ElearnUser record);
    
    ElearnUser selectByUsername(String username);
    
    ElearnUser selectByOpenid(String openid);
}