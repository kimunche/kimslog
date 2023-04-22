package com.kimslog.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class PostSearch {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10; //page 당 출력

   // @Builder
//    public PostSearch(Integer page, Integer size) {
//        this.page = page == null ? 1 : page;
//        this.size = size == null ? 20 : size;
//    }

    public long getOffset(){
        return (long) (Math.max(1,page) -1)* Math.min(size,2000);
    }
}
