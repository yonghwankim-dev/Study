package com.nemo.effective_java.item55.step05;

import java.util.Optional;

public class ProcessHandleClient {
	public static void main(String[] args) {
		Optional<ProcessHandle> parentProcess = ProcessHandle.current().parent();
		System.out.println("parent PID : " + (parentProcess.isPresent() ? parentProcess.get().pid() : "N/A"));

		System.out.println("parent PID : " + parentProcess.map(ProcessHandle::pid).map(String::valueOf).orElse("N/A"));
	}
}
