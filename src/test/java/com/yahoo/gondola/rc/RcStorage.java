/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the New BSD License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.gondola.rc;

import com.yahoo.gondola.Gondola;
import com.yahoo.gondola.LogEntry;
import com.yahoo.gondola.Storage;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RcStorage implements Storage {
    boolean operational = true;

    Map<Integer, RcStorageMember> members = new ConcurrentHashMap<>();

    public RcStorage(Gondola gondola, String hostId) throws Exception {
    }

    @Override
    public boolean isOperational() {
        return operational;
    }

    @Override
    public String getAddress(int memberId) throws Exception {
        return getMember(memberId).address;
    }

    @Override
    public void setAddress(int memberId, String address) throws Exception {
        getMember(memberId).address = address;
    }

    @Override
    public void saveVote(int memberId, int currentTerm, int votedFor) throws Exception {
        getMember(memberId).saveVote(currentTerm, votedFor);
    }

    @Override
    public boolean hasLogEntry(int memberId, int term, int index) throws Exception {
        return getMember(memberId).hasLogEntry(term, index);
    }

    @Override
    public int getCurrentTerm(int memberId) throws Exception {
        return getMember(memberId).currentTerm;
    }

    @Override
    public int getVotedFor(int memberId) throws Exception {
        return getMember(memberId).votedFor;
    }

    @Override
    public int getMaxGap(int memberId) throws Exception {
        return getMember(memberId).maxGap;
    }

    @Override
    public void setMaxGap(int memberId, int maxGap) throws Exception {
        getMember(memberId).setMaxGap(maxGap);
    }

    @Override
    public String getPid(int memberId) throws Exception {
        return getMember(memberId).pid;
    }

    @Override
    public void setPid(int memberId, String pid) throws Exception {
        getMember(memberId).pid = pid;
    }

    @Override
    public int count(int memberId) throws Exception {
        return getMember(memberId).count();
    }

    @Override
    public LogEntry getLogEntry(int memberId, int index) throws Exception {
        return getMember(memberId).getLogEntry(index);
    }

    @Override
    public LogEntry getLastLogEntry(int memberId) throws Exception {
        return getMember(memberId).getLastLogEntry();
    }

    @Override
    public void appendLogEntry(int memberId, int term, int index,
                               byte[] buffer, int bufferOffset, int bufferLen) throws Exception {
        getMember(memberId).appendLogEntry(term, index, buffer, bufferOffset, bufferLen);
    }

    @Override
    public void delete(int memberId, int index) throws Exception {
        getMember(memberId).delete(index);
    }

    @Override
    public void checkin(LogEntry entry) {
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        members.clear();
    }

    RcStorageMember getMember(int memberId) throws Exception {
        RcStorageMember member = members.get(memberId);
        if (member == null) {
            member = new RcStorageMember(this, memberId);
            members.putIfAbsent(memberId, member);
        }
        return member;
    }

    LogEntry checkout() {
        // Isn't called
        return null;
    }
}
