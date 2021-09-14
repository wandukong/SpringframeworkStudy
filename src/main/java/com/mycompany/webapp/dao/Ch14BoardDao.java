package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface Ch14BoardDao {
	public List<Ch14Board> selectByPage(Pager pager);
	public int count();
	public Ch14Board selectByBno(int bno);
	public int insert(Ch14Board board);
	public int delete(int bno);
	public int update(Ch14Board board);
}
