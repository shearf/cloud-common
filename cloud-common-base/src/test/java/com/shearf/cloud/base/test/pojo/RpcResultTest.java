package com.shearf.cloud.base.test.pojo;

import com.shearf.cloud.base.pojo.BaseDTO;
import com.shearf.cloud.base.pojo.RpcResult;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.junit.jupiter.api.Test;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/5/29 15:35
 */
public class RpcResultTest {

    @Test
    public void toStringTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("xiahaihu");
        RpcResult<Object> success = RpcResult.success(userDTO);
        System.out.println(success);
    }

    static class UserDTO extends BaseDTO {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
