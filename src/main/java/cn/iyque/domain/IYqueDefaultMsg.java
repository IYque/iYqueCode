package cn.iyque.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "iyque_default_msg")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IYqueDefaultMsg {

    //主键为id且自增
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //默认欢迎语
    private String defaultContent;

}
