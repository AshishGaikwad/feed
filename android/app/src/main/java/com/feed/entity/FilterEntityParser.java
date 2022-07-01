package com.feed.entity;

import java.util.List;

public class FilterEntityParser {
    private boolean status;
    private int responseCode;
    private String responseMsg;
    private List<FilterEntity> body;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public List<FilterEntity> getBody() {
        return body;
    }

    public void setBody(List<FilterEntity> body) {
        this.body = body;
    }

    public static class FilterEntity {

        private String filterId;
        private String filterName;
        private String filterDescription;
        private String filterPath;
        private String filterIcon;

        public String getFilterId() {
            return filterId;
        }
        public void setFilterId(String filterId) {
            this.filterId = filterId;
        }
        public String getFilterName() {
            return filterName;
        }
        public void setFilterName(String filterName) {
            this.filterName = filterName;
        }
        public String getFilterPath() {
            return filterPath;
        }
        public void setFilterPath(String filterPath) {
            this.filterPath = filterPath;
        }
        public String getFilterIcon() {
            return filterIcon;
        }
        public void setFilterIcon(String filterIcon) {
            this.filterIcon = filterIcon;
        }

        public String getFilterDescription() {
            return filterDescription;
        }
        public void setFilterDescription(String filterDescription) {
            this.filterDescription = filterDescription;
        }


        @Override
        public String toString() {
            return "FilterEntity [filterId=" + filterId + ", filterName=" + filterName + ", filterDescription="
                    + filterDescription + ", filterPath=" + filterPath + ", filterIcon=" + filterIcon + "]";
        }
    }
}
