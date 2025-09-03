package com.example.teamtest.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingDTO {

	    private String nickname;
	    private Integer lolScore;
	    private Integer bgScore;
	    private Integer scScore;
	    private Integer msScore;
	    private Integer loaScore;
	    private Integer totalScore;
	
}
