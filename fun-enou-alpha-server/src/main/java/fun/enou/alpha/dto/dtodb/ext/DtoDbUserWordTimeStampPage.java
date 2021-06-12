package fun.enou.alpha.dto.dtodb.ext;

import java.sql.Timestamp;

public class DtoDbUserWordTimeStampPage {
    private Long userId;
    private Timestamp updatedTimestamp;
    private int offset;
    private int limit;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }
    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    
}
