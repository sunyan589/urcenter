/**
 * 〈一句话功能简述〉<br>
 * 〈功能权限树相关的〉
 *
 * @author lwx
 * @create 2018/11/5
 * @since 1.0.0
 */
package com.yks.urc.funcjsontree.bp.api;

import com.yks.urc.vo.FuncTreeVO;
import com.yks.urc.vo.ResultVO;

import java.util.List;

public interface IFuncJsonTreeBp {
    /**
     *  删除树节点
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/5 11:04
     */
    ResultVO deleteSysPermitNode(FuncTreeVO funcTreeVO);

    /**
     *  更新权限树的名称
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/5 11:04
     */
    ResultVO updateSysPermitNode(FuncTreeVO funcTreeVO);
}
