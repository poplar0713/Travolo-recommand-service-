package com.tistory.lte36.travolo2.dao;

public class SignUpSqls {
	public static final String MEMBER_CHECK="SELECT uid FROM client WHERE uname = :uname AND birthdate = :birthdate";
	public static final String ADD_USER="INSERT INTO client(uid, pwd, uname, gender, birthdate, pass) VALUES(:uid, :pwd, :uname, :gender, :birthdate, :pass)";

//	public static final String SELECT_TID="SELECT tid FROM crawling_tour WHERE label = :tlabel";
	public static final String PLUS_VOTE_COUNT="UPDATE crawling_tour SET vote_count = vote_count+1 WHERE tid = :tid";
	public static final String CHANGE_PASS="UPDATE client SET pass=1 WHERE uid=:uid";
}
