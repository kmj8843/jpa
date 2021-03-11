package com.rlalsa8843.oauth2.jpa.service;

import com.rlalsa8843.oauth2.jpa.domain.Posts;
import com.rlalsa8843.oauth2.jpa.domain.PostsListResponseDto;
import com.rlalsa8843.oauth2.jpa.domain.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanAll() {
        postsRepository.deleteAll();
    }

    @Test
    public void findAllDesc_게시글_정렬후_조회() {
        //given
        postsRepository.save(Posts.builder()
                .title("title1")
                .author("author1")
                .content("content1")
                .build());

        postsRepository.save(Posts.builder()
                .title("title2")
                .author("author2")
                .content("content2")
                .build());

        //when
        List<Posts> result = postsRepository.findAllDesc();

        //then
        System.out.println(result.toString());
        assertThat(result.get(0).getCreateDate()).isAfter(result.get(1).getCreateDate());
    }
}