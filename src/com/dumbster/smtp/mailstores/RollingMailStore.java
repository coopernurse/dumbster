package com.dumbster.smtp.mailstores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dumbster.smtp.MailMessage;
import com.dumbster.smtp.MailStore;

public class RollingMailStore implements MailStore {

	private final Logger logger = LoggerFactory.getLogger(RollingMailStore.class);
    private List<MailMessage> receivedMail;

    public RollingMailStore() {
        receivedMail = Collections.synchronizedList(new ArrayList<MailMessage>());
    }

    @Override
	public int getEmailCount() {
        return receivedMail.size();
    }

    @Override
	public void addMessage(MailMessage message) {
    	logger.info("\n\nReceived message:\n" + message);
        receivedMail.add(message);
        if (getEmailCount() > 100) {
            receivedMail.remove(0);
        }
    }

    @Override
	public MailMessage[] getMessages() {
        return receivedMail.toArray(new MailMessage[receivedMail.size()]);
    }

    @Override
	public MailMessage getMessage(int index) {
        return receivedMail.get(index);
    }

    @Override
    public void clearMessages() {
        this.receivedMail.clear();
    }
}
