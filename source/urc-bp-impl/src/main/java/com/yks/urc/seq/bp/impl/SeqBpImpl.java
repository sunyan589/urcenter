package com.yks.urc.seq.bp.impl;

import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import com.yks.distributed.cache.core.AtomicSequenceGenerator;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.seq.bp.api.ISeqBp;

//@Component
public class SeqBpImpl implements ISeqBp {

	public long getNextSeq(String strSeqName) {
		String seqName = strSeqName;
		String strSeq = AtomicSequenceGenerator.generateSequenceStr(seqName);
		System.out.println(strSeq);
		String lSeq = strSeq.substring(seqName.length() + 10);
		long lNow = System.currentTimeMillis();
		long uuid = Long.parseLong(String.format("%s%s", lNow, lSeq));
		return uuid;
	}

	public long getNextRoleId() {
		return getNextSeq("seq_roleId");
	}

	@Override
	public long getNextDataRuleId() {
		return getNextSeq("seq_dataRuleId");
	}

	@Override
	public long getNextDataRuleTemplId() {
		return getNextSeq("seq_dataRuleTemplId");
	}

	@Override
	public long getNextDataRuleSysId() {
		return getNextSeq("seq_dataRuleSysId");
	}

	@Override
	public long getExpressionId() {
		return getNextSeq("seq_expressionId");
	}

	@Override
	public long getNextAuthWayId() {
		return getNextSeq("seq_authWayId");

	}

	@Override
	public long getNextDataRuleObjId() {
		return getNextSeq("seq_dataRuleObjId");
	}
}
