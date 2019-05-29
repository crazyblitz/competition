package com.ley.competition;

import com.ley.springboot.generator.utils.CodeUtils;
import org.junit.Test;

public class CodeUtilsTest {

    @Test
    public void codeGenerator() {
        CodeUtils.codeGenerate("tb_information", "business");
//        CodeUtils.codeGenerate("business");
    }
}
