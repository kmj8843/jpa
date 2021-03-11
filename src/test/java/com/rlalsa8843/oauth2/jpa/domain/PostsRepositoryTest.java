package com.rlalsa8843.oauth2.jpa.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanAll() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_작성() {
        //given
        String title = "게시글 제목";
        String content = "게시글 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("rlalsa8843@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertEquals(posts.getTitle(), title);
        assertEquals(posts.getContent(), content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2020, 3, 17, 0 ,0, 0);
        postsRepository.save(Posts.builder()
                                .title("title")
                                .content("content")
                                .author("author")
                                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>> createDate: " + posts.getCreateDate() +
                ", modifiedDate: " + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

    @Test
    public void 게시글_삭제() {
        //given
        postsRepository.save(Posts.builder()
                .title("title")
                .author("author")
                .content("content")
                .build());

        postsRepository.save(Posts.builder()
                .title("title2")
                .author("author2")
                .content("content2")
                .build());

        //when
        int size = Optional.ofNullable(postsRepository.count())
                        .orElse(0L).intValue() - 1;

        postsRepository.delete(
                postsRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."))
        );

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(size);
    }
}

/*
 * @After
 *  Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드
 *  보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
 *  여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있다.
 * postsRepository.save
 *  테이블 posts에 insert/update 쿼리를 실행한다.
 *  id 값이 있다면 update, 없다면 insert 쿼리가 실행된다.
 * postsRepository.findAll
 *  테이블 posts에 있는 모든 데이터를 조회해오는 메소드
 * @SpringBootTest
 *  별다른 설정 없으면 H2 데이터베이스를 자동으로 실행해준다.
 */