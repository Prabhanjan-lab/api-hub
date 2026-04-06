/**
 *
 */
package de.sulzer.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import de.sulzer.model.util.Constants;

/**
 * @author Bege
 *
 */
public class Info implements IJson {

    private String project;
    private String summary;
    private String description;
    private String user;
    private String version;
    private String revision;
    private String startDate;
    private String finishDate;
    private String assignee;
    private String reporter;
    private String testEnvironments;
    private String environment;
    private String testPlanKey;

    private static final String FIELD_TEST_ENVIRONMENTS = "testEnvironments";
    private static final String FIELD_TEST_PLAN_KEY = "testPlanKey";
    private static final String FIELD_REPORTER = "reporter";
    private static final String FIELD_ASSIGNEE = "assignee";
    private static final String FIELD_FINISH_DATE = "finishDate";
    private static final String FIELD_START_DATE = "startDate";
    private static final String FIELD_REVISION = "revision";
    private static final String FIELD_VERSION = "version";
    private static final String FIELD_USER = "user";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_SUMMARY = "summary";
    private static final String FIELD_PROJECT = "project";

    public Info() {
        super();
    }

    public Info(String project, String summary, String description, String user, String version, String revision,
            String startDate, String finishDate, String assignee, String reporter, String testEnvironments, String environment) {

        super();

        this.project = project;
        this.summary = summary;
        this.description = description;
        this.user = user;
        this.version = version;
        this.revision = revision;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.assignee = assignee;
        this.reporter = reporter;
        this.testEnvironments = testEnvironments;
        this.environment = environment;
    }

    /**
     * @return the project
     */
    public String getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(String project) {
        this.project = project;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * @param revision the revision to set
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the assginee
     */
    public String getAssginee() {
        return assignee;
    }

    /**
     * @param assginee the assginee to set
     */
    public void setAssginee(String assginee) {
        this.assignee = assginee;
    }

    /**
     * @return the reporter
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * @param reporter the reporter to set
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    /**
     * @return the assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the testEnvironments
     */
    public String getTestEnvironments() {
        return testEnvironments;
    }

    /**
     * @param testEnvironments the testEnvironments to set
     */
    public void setTestEnvironments(String testEnvironments) {
        this.testEnvironments = testEnvironments;
    }

    /**
     * @return the environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * @return the testPlanKey
     */
    public String getTestPlanKey() {
        return testPlanKey;
    }

    /**
     * @param testPlanKey the testPlanKey to set
     */
    public void setTestPlanKey(String testPlanKey) {
        this.testPlanKey = testPlanKey;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + ((finishDate == null) ? 0 : finishDate.hashCode());
        result = prime * result + ((project == null) ? 0 : project.hashCode());
        result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
        result = prime * result + ((revision == null) ? 0 : revision.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + ((testEnvironments == null) ? 0 : testEnvironments.hashCode());
        result = prime * result + ((testPlanKey == null) ? 0 : testPlanKey.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Info other = (Info) obj;
        if (assignee == null) {
            if (other.assignee != null)
                return false;
        } else if (!assignee.equals(other.assignee))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (environment == null) {
            if (other.environment != null)
                return false;
        } else if (!environment.equals(other.environment))
            return false;
        if (finishDate == null) {
            if (other.finishDate != null)
                return false;
        } else if (!finishDate.equals(other.finishDate))
            return false;
        if (project == null) {
            if (other.project != null)
                return false;
        } else if (!project.equals(other.project))
            return false;
        if (reporter == null) {
            if (other.reporter != null)
                return false;
        } else if (!reporter.equals(other.reporter))
            return false;
        if (revision == null) {
            if (other.revision != null)
                return false;
        } else if (!revision.equals(other.revision))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (summary == null) {
            if (other.summary != null)
                return false;
        } else if (!summary.equals(other.summary))
            return false;
        if (testEnvironments == null) {
            if (other.testEnvironments != null)
                return false;
        } else if (!testEnvironments.equals(other.testEnvironments))
            return false;
        if (testPlanKey == null) {
            if (other.testPlanKey != null)
                return false;
        } else if (!testPlanKey.equals(other.testPlanKey))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append(Constants.APO);
        sb.append("info");
        sb.append(Constants.APO);
        sb.append(" : {");
        sb.append(JSONFormatting.fieldToJSON(FIELD_PROJECT, this.project));
        sb.append(JSONFormatting.fieldToJSON(FIELD_SUMMARY, this.summary));
        sb.append(JSONFormatting.fieldToJSON(FIELD_DESCRIPTION, this.description));
        sb.append(JSONFormatting.fieldToJSON(FIELD_USER, this.user));
        sb.append(JSONFormatting.fieldToJSON(FIELD_VERSION, this.version));
        sb.append(JSONFormatting.fieldToJSON(FIELD_REVISION, this.revision));
        sb.append(JSONFormatting.fieldToJSON(FIELD_START_DATE, this.startDate));
        sb.append(JSONFormatting.fieldToJSON(FIELD_FINISH_DATE, this.finishDate));
        sb.append(JSONFormatting.fieldToJSON(FIELD_ASSIGNEE, this.assignee));
        sb.append(JSONFormatting.fieldToJSON(FIELD_REPORTER, this.reporter));
        sb.append(JSONFormatting.fieldToJSON(FIELD_TEST_PLAN_KEY, this.testPlanKey));
//		sb.append(JSONFormatting.fieldToJSON("testEnvironments", this.testEnvironments));
        //sb.append(JSONFormatting.fieldToJSON("environment", this.environment));
                sb.append("\"testEnvironments\" : " + "[\"" + this.testEnvironments+ "\"]");
        sb.append("}");
//		sb.append(",\"fields\":{\"environment\":\"Demo_ECE\"}");

        // removing last comma
        String infoString = sb.toString();
        infoString = infoString.replaceAll(Constants.LISTEND_v3, "}");

        return infoString;
    }

    public JsonObject getAsJson() {

        JsonObjectBuilder info = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(info, FIELD_PROJECT, this.project);
        JSONFormatting.addKeyValuePair(info, FIELD_SUMMARY, this.summary);
        JSONFormatting.addKeyValuePair(info, FIELD_DESCRIPTION, this.description);
        JSONFormatting.addKeyValuePair(info, FIELD_USER, this.user);
        JSONFormatting.addKeyValuePair(info, FIELD_VERSION, this.version);
        JSONFormatting.addKeyValuePair(info, FIELD_REVISION, this.revision);
        JSONFormatting.addKeyValuePair(info, FIELD_START_DATE, this.startDate);
        JSONFormatting.addKeyValuePair(info, FIELD_FINISH_DATE, this.finishDate);
        JSONFormatting.addKeyValuePair(info, FIELD_ASSIGNEE, this.assignee);
        JSONFormatting.addKeyValuePair(info, FIELD_REPORTER, this.reporter);
        JSONFormatting.addKeyValuePair(info, FIELD_TEST_PLAN_KEY, this.testPlanKey);

        if (this.testEnvironments != null && this.testEnvironments.length() > 0) {
            info.add(FIELD_TEST_ENVIRONMENTS, Json.createArrayBuilder().add(this.testEnvironments));
        }

        return info.build();

    }

}
