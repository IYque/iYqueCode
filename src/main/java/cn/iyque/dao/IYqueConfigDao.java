package cn.iyque.dao;

import cn.iyque.domain.IYqueConfig;
import cn.iyque.domain.IYqueDefaultMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IYqueConfigDao  extends JpaRepository<IYqueConfig,Integer> {
}
