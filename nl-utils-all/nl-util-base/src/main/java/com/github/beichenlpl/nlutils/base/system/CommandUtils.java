package com.github.beichenlpl.nlutils.base.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author beichenlpl
 * @since 2023/09/06
 */
public class CommandUtils {
    public static final Runtime CURRENT_RUNTIME = Runtime.getRuntime();

    private static final Map<String, Process> PROCESS_MAP = new HashMap<>();

    public synchronized static List<String> exec(String cmd) throws IOException {
        Process process = CURRENT_RUNTIME.exec(cmd);
        List<String> result = new ArrayList<>();

        BufferedReader infoReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String info;
        while ((info = infoReader.readLine()) != null) {
            result.add(info);
        }
        infoReader.close();

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String error;
        while ((error = errorReader.readLine()) != null) {
            result.add(error);
        }
        errorReader.close();

        return result;
    }

    public synchronized String startProcess(String startCmd) throws IOException {
        Process process = CURRENT_RUNTIME.exec(startCmd);
        UUID processId = UUID.randomUUID();
        PROCESS_MAP.put(processId.toString(), process);
        return processId.toString();
    }

    public synchronized void stopProcess(String processId) {
        PROCESS_MAP.get(processId).destroy();
    }

    public synchronized Set<String> getProcessIds() {
        return PROCESS_MAP.keySet();
    }
}
