package com.example.store_analysis.controller;

import com.example.store_analysis.domain.*;
import com.example.store_analysis.naverapi.Keyword;
import com.example.store_analysis.naverapi.NaverAPI;
import com.example.store_analysis.naverapi.Category;
import com.example.store_analysis.recommedation.RecommendationSystem;
import com.example.store_analysis.repository.ProductRepository;
import com.example.store_analysis.repository.UsersRepository;
import com.example.store_analysis.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private UsersRepository usersRepository;
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;


    @Autowired
    public Controller(UsersRepository usersRepository, ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.usersRepository = usersRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/users")
    public List<Users> retrieveAllUsers() {
        return usersRepository.findAll();
    }

    //Sign up
    @PostMapping("/sign_up")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        // 사용자 아이디 중복 확인
        if (usersRepository.existsById(user.getUser_id())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409: 리소스 충돌 발생(이메일 중복 등 발생시)
        }

        usersRepository.save(user);
        return ResponseEntity.created(null).build();
    }

    //Log in
    @PostMapping("/log_in")
    public ResponseEntity<String> login(@RequestBody Users user) {
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
    public String naverCategoryAPI(@RequestBody Category category) {
        NaverAPI naverAPI = new NaverAPI();
        return naverAPI.categoryAPI(category.request);
    }

    @PostMapping("/naver_api/keyword")
    public String naverKeywordAPI(@RequestBody Keyword keyword) {
        NaverAPI naverAPI = new NaverAPI();
        return naverAPI.keywordAPI(keyword.request);
    }

    @GetMapping("/recom/{user_id}")
    public String recommendationSystem(@PathVariable String user_id) throws IOException, InterruptedException {
        //유저 아이디를 받으면 product 테이블에서 조회해서 findOne(user_id)로 성별, 키, 몸무게 받아온다.
        //받아온 정보를 RecommendationSystem 객체에 넘긴다.
        RecommendationSystem recom = new RecommendationSystem("M", 164.0F, 53.0F);
        return recom.pythonOutput;
    }

    @PostMapping("/user_type")
    @ResponseBody
    public Optional<Product> handleUserTypeRequest(@RequestBody Users user) {
        List<Review> reviews = reviewRepository.findAll();
        Map<Double, Map<Double, List<Review>>> groupedReviews = reviews.stream().collect
                (Collectors.groupingBy(review -> roundToNearestMultipleOf5(review.getWeight(), 5),
                        Collectors.groupingBy(review -> roundToNearestMultipleOf5(review.getHeight(), 5))));

        double weight = roundToNearestMultipleOf5(user.getWeight(), 5);
        double height = roundToNearestMultipleOf5(user.getHeight(), 5);

        // 검색 키를 사용하여 groupedReviews에서 List<Review>를 가져옴
        List<Review> reviewsForWeightAndHeight = groupedReviews.get(weight).get(height);

        // URL을 세서 갯수를 맵으로 만듦
        Map<String, Long> urlCountMap = reviewsForWeightAndHeight.stream()
                .collect(Collectors.groupingBy(Review::getUrl, Collectors.counting()));

        // 가장 많이 나온 URL 찾기
        String mostFrequentUrl = urlCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        Optional<Product> p = productRepository.findById(mostFrequentUrl);
        return p;
    }

    @PostMapping("/effectiveness")
    @ResponseBody
    public StoreNameResponse handelEffectiveness(@RequestBody ProductSearchRequest request)
    {

        List<Product> productList = productRepository.findByProductName(request.getProductName());

        Map<String, Integer> storeFrequency = new HashMap<>();
        String mostFrequentStore = null;
        int maxFrequency = 0;

        for (Product product : productList) {
            String store = product.getStore();
            int frequency = storeFrequency.getOrDefault(store, 0) + 1;
            storeFrequency.put(store, frequency);

            if (frequency > maxFrequency) {
                mostFrequentStore = store;
                maxFrequency = frequency;
            }
        }

        System.out.println("Most frequent store: " + mostFrequentStore);


        // StoreNameResponse 객체를 생성하여 반환
        StoreNameResponse response = new StoreNameResponse();
        response.setStoreName(mostFrequentStore);
        return response;
    }

    // 주어진 값을 가장 가까운 multipleOf에 반올림하는 메서드
    private double roundToNearestMultipleOf5(double value, int multipleOf) {
        return Math.round(value / multipleOf) * multipleOf;
    }

}
