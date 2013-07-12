package org.schemaanalyst.datageneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlwriter.SQLWriter;

public abstract class GoalReport {

    protected Data data;
    protected boolean success;
    protected long timeToGenerate, startTime, endTime;

    public GoalReport() {
        success = false;
        initializeTimings();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean wasSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    protected void initializeTimings() {
        startTime = 0;
        endTime = 0;
        timeToGenerate = 0;
    }

    public void startTimer() {
        initializeTimings();
        startTime = System.currentTimeMillis();
    }

    public void endTimer() {
        endTime = System.currentTimeMillis();
        timeToGenerate = endTime - startTime;
    }

    public long getTimeToGenerate() {
        return timeToGenerate;
    }

    public abstract String getDescription();

    public void appendDataToStringBuilder(StringBuilder sb) {
        SQLWriter sqlWriter = new SQLWriter();
        for (String statement : sqlWriter.writeInsertStatements(data)) {
            if (!wasSuccess()) {
                sb.append("-- ");
            }
            sb.append(statement);
            sb.append(";");
            sb.append("\n");
        }
    }

    public void appendToStringBuilder(StringBuilder sb) {
        sb.append("-- ");
        sb.append(getDescription());
        sb.append("\n");

        sb.append("-- * Success: ");
        sb.append(wasSuccess());
        sb.append("\n");

        sb.append("-- * Time: ");
        sb.append(getTimeToGenerate());
        sb.append("ms \n");

        appendDataToStringBuilder(sb);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendToStringBuilder(sb);
        return sb.toString();
    }
}
