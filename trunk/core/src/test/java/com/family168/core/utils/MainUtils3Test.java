package com.family168.core.utils;

import java.util.*;

import junit.framework.*;


public class MainUtils3Test extends TestCase {
    // 只要不出现java.lang.StackOverflowError死循环，就说明成功了
    public void testCopy() {
        ContractBean contractBean = new ContractBean();
        Contract contract = contractBean.getContract();
    }

    static class ContractBean {
        private Contract contract = new Contract();

        public Contract getContract() {
            MainUtils.copy(this, contract);

            return contract;
        }
    }

    static class Contract {
    }
}
