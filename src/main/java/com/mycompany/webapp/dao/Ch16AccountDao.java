package com.mycompany.webapp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.mycompany.webapp.dto.Ch16Account;

@Mapper
public interface Ch16AccountDao {

	public List<Ch16Account> selectAll();
	public Ch16Account selectByAno(int ano);
	public int updateBalance(Ch16Account account);
}
