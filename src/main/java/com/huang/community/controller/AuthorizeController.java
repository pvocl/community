package com.huang.community.controller;

import com.huang.community.commons.GithubAuthorizeHelper;
import com.huang.community.dto.AccessTokenDTO;
import com.huang.community.entity.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubAuthorizeHelper githubAuthorizeHelper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, Model model) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        String token = githubAuthorizeHelper.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubAuthorizeHelper.getGithubUser(token);
        model.addAttribute("user", githubUser);
        return "index";
    }
}
