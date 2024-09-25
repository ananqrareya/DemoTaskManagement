package com.ListTasks.LTasks.repository;

import com.ListTasks.LTasks.entity.SubTask;
import org.eclipse.angus.mail.imap.protocol.INTERNALDATE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask,Integer> {
}
