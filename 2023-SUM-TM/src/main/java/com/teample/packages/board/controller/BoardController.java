package com.teample.packages.board.controller;

import com.teample.packages.board.dto.BoardDto;
import com.teample.packages.board.service.BoardService;
import com.teample.packages.member.domain.Member;
import com.teample.packages.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.script.ScriptContext;
import java.util.List;


@Controller
@AllArgsConstructor
@Slf4j
public class BoardController {
    private BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/board")
    public String list( Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/BoardList.html";
    }

    /* 게시글 작성 페이지 */
    @GetMapping("/post")
    public String list() {
        return "board/CreateBoard.html";
    }

    /* 게시글 작성 처리 */
    @PostMapping("post")
    public String write(BoardDto boardDto,BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "board/CreateBoard.html";
        }

        // 게시글 저장 로직
        boardService.savePost(boardDto);
        return "redirect:/board";
    }


    /* 게시글 상세 페이지 */
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) throws Exception {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/BoardDetail.html";
    }

    /* 게시글 수정 페이지*/
    @GetMapping("/post/edit/{no}")
    public String edit(@SessionAttribute(name = "loginId", required = true) Member authorId, @PathVariable("no") Long no, Model model) throws Exception {
        BoardDto boardDTO = boardService.getPost(no);
        /*if (!(boardDTO.getWriter().equals(authorId.getLoginId()))) {
            return "redirect:/board/BoardList.html";
        }*/
        model.addAttribute("boardDto", boardDTO);
        return "board/BoardEdit.html";
    }

    /* 게시글 수정 처리*/
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/board";
    }

    /* 게시글 삭제 처리*/
    @DeleteMapping("/post/{no}")
    public String delete(@SessionAttribute(name = "loginId", required = true) Member authorId, @PathVariable("no") Long no) throws Exception {
        BoardDto boardDTO = boardService.getPost(no);
        /*
        if (!(boardDTO.getWriter().equals(authorId.getLoginId()))) {
            return "redirect:/board/BoardList";
        }*/
        boardService.deletePost(no);

        return "redirect:/board";
    }

    /* 게시글 검색 */
    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/BoardList.html";
    }

}