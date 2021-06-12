package fun.enou.alpha.dto.dtodb.ext;

public class DtoDbUserWordPage {
    
    private Long userId;
    private int offset;
    private int limit = 100;

    public DtoDbUserWordPage(Long userId, int offset, int limit){
        this.userId = userId;
        this.offset = offset;
        this.limit = limit;
    }


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
