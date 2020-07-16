package chengweiou.universe.milkyway.model;


import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchCondition extends AbstractSearchCondition {
    private Long minId;
}
