package cn.iocoder.yudao.module.callcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static cn.iocoder.yudao.module.callcenter.enums.CommandType.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommandKey {

    Originate(Async),
    UuidKill(Async),
    Bridge(Msg),
    ;

    private CommandType type;

}
