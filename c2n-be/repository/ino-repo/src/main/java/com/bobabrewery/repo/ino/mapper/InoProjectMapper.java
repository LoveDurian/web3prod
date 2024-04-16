package com.bobabrewery.repo.ino.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.ino.domain.InoProject;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface InoProjectMapper {

    int deleteById(Long id);

    int create(InoProject record);

    int creates(List<InoProject> records);

    int createHas(InoProject record);

    Long count(InoProject where);

    List<InoProject> list(PageParam page, InoProject where);

    InoProject findById(Long id);

    List<InoProject> findByIds(List<Long> ids);

    int updateByIdHas(InoProject record);

    int updateById(InoProject record);
}
