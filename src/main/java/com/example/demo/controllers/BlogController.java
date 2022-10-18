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
import org.springframework.web.bind.annotation.*;

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
    public String blogProfile(Model model)
    {
        Iterable<Profile> profiles = profileRepository.findAll();
        model.addAttribute("profiles", profiles);
        return "blog-profile";
    }

    @PostMapping("/blog/profile")
    public String blogPostProfile(@RequestParam String nickname,
                                  @RequestParam String surename,
                                  @RequestParam String name,
                                  @RequestParam String patron,
                                  @RequestParam String about_me,
                                  @RequestParam int age, Model model) {
        Profile profile = new Profile(nickname, surename, name, patron, about_me, age);
        profileRepository.save(profile);
        model.addAttribute("nickname", nickname);
        model.addAttribute("surename", surename);
        model.addAttribute("name", name);
        model.addAttribute("patron", patron);
        model.addAttribute("about_me", about_me);
        String str2 = Integer.toString(age);
        model.addAttribute("age", str2);
        return "blog-profile";
    }

    @GetMapping("/blog/comm")
    public String blogComm(Model model) {

        Iterable<Comm> comments = commRepository.findAll();
        model.addAttribute("comm", comments);

        return "blog-comm";
    }

    @PostMapping("blog/comm")
    public String createComm(@RequestParam String caption,
                                @RequestParam String comm,
                                @RequestParam String time,
                                @RequestParam String author,
                                @RequestParam String mark, Model model){
        Comm comment = new Comm(caption, comm, time, author, mark);
        commRepository.save(comment);
        model.addAttribute("caption", caption);
        model.addAttribute("comm", comm);
        model.addAttribute("time", time);
        model.addAttribute("author", author);
        model.addAttribute("mark", mark);
        return "blog-comm";
    }

    @GetMapping("/blog/profiles/{id}")
    public String blogDetailsProfiles(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Profile> profiles = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profiles.ifPresent(res::add);
        model.addAttribute("profiles", res);
        if(!profileRepository.existsById(id))
        {
            return "redirect:/blog";
        }

        return "blog-details-profiles";
    }

    @GetMapping("/blog/profiles/{id}/edit")
    public String blogEditProfiles(@PathVariable("id") long id, Model model)
    {
        if(!profileRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Profile> profile = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profile.ifPresent(res::add);
        model.addAttribute("profiles", res);
        return "blog-edit-profiles";

    }

    @PostMapping("blog/profiles/{id}/edit")
    public String blogProfilesUpdate(@PathVariable("id") long id,
                                     @RequestParam String nickname,
                                     @RequestParam String surename,
                                     @RequestParam String name,
                                     @RequestParam String patron,
                                     @RequestParam String about_me,
                                     @RequestParam int age, Model model) {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profile.setNickname(nickname);
        profile.setSurename(surename);
        profile.setName(name);
        profile.setPatron(patron);
        profile.setAbout_me(about_me);
        profile.setAge(age);
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
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id))
        {
            return "redirect:/blog";
        }

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model)
    {
        if(!postRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";

    }

    @PostMapping("blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
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
        Optional<Comm> comm = commRepository.findById(id);
        ArrayList<Comm> res = new ArrayList<>();
        comm.ifPresent(res::add);
        model.addAttribute("comm", res);
        if(!commRepository.existsById(id))
        {
            return "redirect:/blog";
        }

        return "blog-details-comm";
    }

    @GetMapping("/blog/comm/{id}/edit")
    public String blogEditComm(@PathVariable("id") long id, Model model)
    {
        if(!commRepository.existsById(id))  {
            return "redirect:/blog";
        }

        Optional<Comm> comm = commRepository.findById(id);
        ArrayList<Comm> res = new ArrayList<>();
        comm.ifPresent(res::add);
        model.addAttribute("comm", res);

        return "blog-edit-comm";
    }

    @PostMapping("blog/comm/{id}/edit")
    public String blogUpdateComm(@PathVariable("id") long id,
                                 @RequestParam String caption,
                                 @RequestParam String comm,
                                 @RequestParam String time,
                                 @RequestParam String author,
                                 @RequestParam String mark,
                                  Model model) {

        Comm comms = commRepository.findById(id).orElseThrow();
        comms.setCaption(caption);
        comms.setComm(comm);
        comms.setTime(time);
        comms.setAuthor(author);
        comms.setMark(mark);
        commRepository.save(comms);
        return "redirect:/";
    }

    @PostMapping("/blog/comm/{id}/remove")
    public String blogBlogDeleteComm(@PathVariable("id") long id, Model model) {
        Comm comm = commRepository.findById(id).orElseThrow();
        commRepository.delete(comm);
        return "redirect:/";
    }
}

