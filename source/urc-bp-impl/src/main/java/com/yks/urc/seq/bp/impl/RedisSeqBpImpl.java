package com.yks.urc.seq.bp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import com.yks.distributed.cache.core.AtomicSequenceGenerator;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.seq.bp.api.ISeqBp;

@Component
public class RedisSeqBpImpl implements ISeqBp {

	@Autowired
	private ICacheBp cacheBp;

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			System.out.println((long) (Math.random() * 100000));
		}
	}

	public long getNextSeq(String strSeqName) {
		StringBuilder sb = new StringBuilder();
		sb.append("000000");
		sb.append(cacheBp.getNextSeq(strSeqName));

		String strSeq = StringUtility.subStringRight(sb.toString(), 6);
//		System.out.println(strSeq);
//		String lSeq = strSeq.substring(seqName.length() + 10);
		long lNow = System.currentTimeMillis();
		long uuid = Long.parseLong(String.format("%s%s", lNow, strSeq));
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
