package cn.iyque.dao;

import cn.iyque.domain.IYqueUserCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IYqueUserCodeDao extends JpaRepository<IYqueUserCode,Integer> {
    IYqueUserCode findByCodeState(String codeState);
}
