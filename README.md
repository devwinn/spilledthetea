# spilledthetea

Spilled The Tea is a Blog site. 

A Java based application utilizing Springboot, Thymeleaf, and Bootstrap. 

Anyone who visits the site will see the landing page which features 'Recent Posts'.
Any other functionality will require that users be logged in to the site.


#### Role User -
To Create A User > Email, User Name, Password, First Name, Last Name.
Once logged in, a user can visit the 'All Posts' page which features a list of all posts by all users. They will also have the ability to Create their own posts.

#### Role Admin - 
Admin can not be created through front end.
Once logged in, an admin has all privledges of a User with additional Admin only privledges
Additional admin privleges include create/delete categories, users, and posts.

#### Posts - 
Consist of a heading, content, and the ability to add categories to the post.


Relational Diagram -
![Er Diagram](/src/main/resources/static/images/ER_Diagram.png)


User Stories - <br>
Role User - <br>
As a user I want to be able to create and update my account. <br>
As a user I want to create a blog post. <br>
As a user I want to see blog posts from other users. <br>
As a user I want to be able to remove blog posts I've made. <br>
As a user I want to add categories to my post. <br>
As a user I want to see posts related to certain topics. <br>
As a user I want other users to comment on my Posts. <br>
<br>
Role Admin - <br>
As an admin I want to see all blog users. <br>
As an admin I want to be able to create/delete users. <br>

![GitHub language count](https://img.shields.io/github/languages/count/devwinn/spilledthetea)
![GitHub top language](https://img.shields.io/github/languages/top/devwinn/spilledthetea?color=yellow)
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/devwinn/spilledthetea)
![GitHub forks](https://img.shields.io/github/forks/devwinn/spilledthetea?style=social)
![GitHub Repo stars](https://img.shields.io/github/stars/{username}/{repo-name}?style=social)
