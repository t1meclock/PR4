package com.example.demo.controllers;

import com.example.demo.models.Comm;
import com.example.demo.models.Profile;
import com.example.demo.repo.CommRepository;
import com.example.demo.repo.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CommRepository commRepository;

    private String _authorOfComment;

    @GetMapping("/")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);

        return "redirect:/";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitleContains(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);

        return "blog-filter";
    }

    @PostMapping("/blog/filter/results")
    public String blogResults(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitle(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);

        return "blog-filter";
    }

    @GetMapping("/blog/profile")
    public String blogProfileAdd(Profile profile, Model model)
    {
        Iterable<Profile> profiles = profileRepository.findAll();
        model.addAttribute("profiles", profiles);

        return "blog-profile";
    }


    @PostMapping("/blog/profile")
    public String blogPostProfile(@ModelAttribute ("profile") @Valid Profile profile,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "blog-profile";
        }

        profileRepository.save(profile);

        return "redirect:/";

    }

    @GetMapping("/blog/comm")
    public String blogComments(Model model) {

        Iterable<Comm> comment = commRepository.findAll();
        model.addAttribute("comment", comment);

        return "blog-comm";
    }

    @GetMapping("/blog/comm/add")
    public String blogCommentsAdd(Comm comm, Model model)
    {
        Iterable<Comm> comms = commRepository.findAll();
        model.addAttribute("comms", comms);

        return "blog-comm-add";
    }


//    String caption,
//    String comnt,
//    String author,
//    @RequestParam String mark,
//      Model model,
    @PostMapping("blog/comm/add")
    public String blogCommentsAdd(@ModelAttribute ("comm") @Valid Comm comm,
                                  BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "blog-comm-add";
        }

//        int mrk = Integer.parseInt(mark);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"

        comm.setActionDate(formattedDateTime);
        commRepository.save(comm);
//        model.addAttribute("caption", caption);
//        model.addAttribute("comnt", comnt);
//        model.addAttribute("author", author);
//        model.addAttribute("mark", mark);
//        model.addAttribute("time", formattedDateTime);

        return "redirect:/blog/comm";
    }

    @GetMapping("/blog/profiles/{id}")
    public String blogDetailsProfiles(@PathVariable(value = "id") long id, Model model)
    {
        if(!profileRepository.existsById(id))
        {
            return "redirect:/";
        }

        Optional<Profile> profiles = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profiles.ifPresent(res::add);
        model.addAttribute("profiles", res);

        return "blog-details-profiles";
    }

    @GetMapping("/blog/profiles/{id}/edit")
    public String blogEditProfiles(@PathVariable(value = "id") long id, Profile profiles, Model model)
    {
        if(!profileRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<Profile> profile = profileRepository.findById(id);
//        ArrayList<Profile> res = new ArrayList<>();
//        profile.ifPresent(res::add);
        model.addAttribute("profile", profile.get());

        return "blog-edit-profiles";
    }

    @PostMapping("blog/profiles/{id}/edit")
    public String blogProfilesUpdate(@ModelAttribute ("profile") @Valid Profile profile, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "blog-edit-profiles";
        }

        profileRepository.save(profile);
        return "redirect:/";
    }

    @PostMapping("/blog/profiles/{id}/remove")
    public String blogBlogProfilesDelete(@PathVariable("id") long id, Model model) {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profileRepository.delete(profile);

        return "redirect:/";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        if(!postRepository.existsById(id))
        {
            return "redirect:/";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Post posts, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<Post> post = postRepository.findById(id);
//        ArrayList<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
        model.addAttribute("post", post.get());

        return "blog-edit";
    }

    @PostMapping("blog/{id}/edit")
    public String blogPostUpdate(@ModelAttribute ("post") @Valid Post post,
                                 BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "blog-edit";
        }

        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogBlogDelete(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }

    @GetMapping("/blog/comm/{id}")
    public String blogDetailsComm(@PathVariable(value = "id") long id, Model model)
    {
        if(!commRepository.existsById(id))
        {
            return "redirect:/";
        }

        Optional<Comm> comm = commRepository.findById(id);
        ArrayList<Comm> res = new ArrayList<>();
        comm.ifPresent(res::add);
        model.addAttribute("comm", res);

        return "blog-details-comm";
    }

    @GetMapping("/blog/comm/{id}/edit")
    public String blogEditComm(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Comm> comm = commRepository.findById(id);
//        ArrayList<Comm> res = new ArrayList<>();
//        comm.ifPresent(res::add);
        model.addAttribute("comm", comm.get ());

        return "blog-edit-comm";
    }


//    @RequestParam String caption,
//    @RequestParam String comnt,
    @PostMapping("/blog/comm/{id}/edit")
    public String blogUpdateComm(@ModelAttribute ("comm") @Valid Comm comm,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "blog-edit-comm";
        }

//        int mrk = comm.getMark();

//        if (mark != null) {
//            mrk = Integer.parseInt(mark);
//        }

//        comm.setCaption(caption);
//        comm.setComnt(comnt);
//        comm.setAuthor(comm.getAuthor());
//        comm.setActionDate(comm.getActionDate());
//        comm.setMark(mrk);
        commRepository.save(comm);

        return "redirect:/blog/comm";
    }

    @PostMapping("/blog/comm/{id}/remove")
    public String blogBlogDeleteComm(@PathVariable("id") long id, Model model) {
        Comm comm = commRepository.findById(id).orElseThrow();
        commRepository.delete(comm);

        return "redirect:/blog/comm";
    }

    @GetMapping("/blog/filter/comm")
    public String blogFilterComments(Model model)
    {
        return "blog-comm-filter";
    }

    @PostMapping("/blog/filter/comment")
    public String blogResultComments(@RequestParam String author, Model model)
    {
        List<Comm> result = commRepository.findByAuthorContains(author);
        model.addAttribute("result", result);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-comm-filter";
    }

    @PostMapping("/blog/filter/comments")
    public String blogResultsComments(@RequestParam String author, Model model)
    {
        List<Comm> result = commRepository.findByAuthor(author);
        model.addAttribute("result", result);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-comm-filter";
    }

    @GetMapping("/blog/filter/profile")
    public String blogFilterProfile(Model model)
    {
        return "blog-profiles-filter";
    }

    @PostMapping("/blog/filter/profile")
    public String blogResultProfile(@RequestParam String nickname, Model model)
    {
        List<Profile> results = profileRepository.findByNicknameContains(nickname);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-profiles-filter";
    }

    @PostMapping("/blog/filter/profiles")
    public String blogResultsProfiles(@RequestParam String nickname, Model model)
    {
        List<Profile> results = profileRepository.findByNickname(nickname);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-profiles-filter";
    }
}

