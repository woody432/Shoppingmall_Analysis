package com.example.store_analysis.controller;

import com.example.store_analysis.domain.Users;
import com.example.store_analysis.naverapi.Keyword;
import com.example.store_analysis.naverapi.NaverAPI;
import com.example.store_analysis.naverapi.Category;
import com.example.store_analysis.recommedation.RecommendationSystem;
import com.example.store_analysis.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller
{
    private UsersRepository usersRepository;

    public Controller(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/users")
    public List<Users> retrieveAllUsers()
    {
        return usersRepository.findAll();
    }

    //Sign up
    @PostMapping("/sign_up")
    public ResponseEntity<Users> createUser(@RequestBody Users user)
    {
        // 사용자 아이디 중복 확인
        if (usersRepository.existsById(user.getUser_id())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409: 리소스 충돌 발생(이메일 중복 등 발생시)
        }

        usersRepository.save(user);
        return ResponseEntity.created(null).build();
    }

    //Log in
    @PostMapping("/log_in")
    public ResponseEntity<String> login(@RequestBody Users user)
    {
        String user_id = user.getUser_id();
        String password = user.getPassword();

        // 데이터베이스에서 사용자 아이디로 사용자 정보 조회
        Optional<Users> userOptional = usersRepository.findById(user_id);

        // 사용자 정보가 존재하고 비밀번호가 일치하면 'Sign up!' 반환
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return ResponseEntity.ok("Sign up!");
        }

        // 사용자 정보가 없거나 비밀번호가 일치하지 않으면 401 Unauthorized 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/naver_api/category")
    public String naverCategoryAPI(@RequestBody Category category)
    {
        NaverAPI naverAPI = new NaverAPI();
        return naverAPI.categoryAPI(category.request);
    }

    @PostMapping("/naver_api/keyword")
    public String naverKeywordAPI(@RequestBody Keyword keyword)
    {
        NaverAPI naverAPI = new NaverAPI();
        return naverAPI.keywordAPI(keyword.request);
    }

    @GetMapping("/recom/{user_id}")
    public String recommendationSystem(@PathVariable String user_id) throws IOException, InterruptedException
    {
        //유저 아이디를 받으면 product 테이블에서 조회해서 findOne(user_id)로 성별, 키, 몸무게 받아온다.
        //받아온 정보를 RecommendationSystem 객체에 넘긴다.
        RecommendationSystem recom = new RecommendationSystem("M", 164.0F, 53.0F);
        return recom.pythonOutput;
    }

}
