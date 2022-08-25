package com.mysite.askAnything;
import com.mysite.askAnything.comment.Comment;
import com.mysite.askAnything.comment.CommentRepository;
import com.mysite.askAnything.post.PostRepository;
import com.mysite.askAnything.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AskAnythingApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService postService;

	@Test
	void testJpa() {
		/*
		Question q1 = new Question();
		q1.setSubject("AskAnything이 무엇인가요?");
		q1.setContent("AskAnything에 대해 알고싶어요");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);		//첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("자바 마스터가 되고싶어요");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);		//두번째 질문 저장
		*/

		/*
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size()); ->2건만 저장했기때문에 사이즈는 2
		Question q = all.get(0); *assertEquals는 (기대값, 실제값)과 같이 사용하고, 동일한지 비교한다.
		assertEquals("AskAnything이 무엇인가요?", q.getSubject());

		*/

		/*
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()){
			Question q = oq.get();
			assertEquals("AskAnything이 무엇인가요?",q.getSubject());
		}

	*/
/*
		Question q = this.questionRepository.findBySubject("AskAnything이 무엇인가요?");
		assertEquals(1, q.getId());
*/

		/*
		Question q = this.questionRepository.findBySubjectAndContent(
				"AskAnything이 무엇인가요?", "AskAnything에 대해 알고싶어요");
			assertEquals(1, q.getId());
		 */

		/*
		List<Question> qList = this.questionRepository.findBySubjectLike("Ask%");
		Question q = qList.get(0);
		assertEquals("AskAnything이 무엇인가요?", q.getSubject());
		*/

		/*
		//데이터 수정하기
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
		 */


		/*
		//데이터 삭제하기
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq= this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
		*/


		/*Answer


		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q); //어떤 질문의 답변인지 알기위해서 Question객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
		*/
	/*
		Optional<Comment> oa = this.commentRepository.findById(1);
		assertTrue(oa.isPresent());
		Comment a = oa.get();
		assertEquals(2, a.getPost().getId());

	*/
		/*
		for(int i=1; i <= 300; i++){
			String subject = String.format("테스트 데이터입니다. : [%03d]", i);
			String content = "내용무";
			this.postService.create(subject, content);
		}
*/
	}



}
