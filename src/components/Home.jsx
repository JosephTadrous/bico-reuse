import React from 'react';
import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';

import Header from './Header';
import './Home.css';
import HomePostList from './HomePostList';
import PostPage from './PostPage';
import CreatePostForm from './CreatePostForm';

import EditProfileForm from './EditProfileForm';
import Profile from './Profile';
import PostUpdateForm from './PostUpdateForm';
import Dashboard from './Dashboard';

import UserPage from './UserComponents/UserPage';


export default function Home() {
	const [posts, setPosts] = useState([]);

	useEffect( () => {
		fetch('http://localhost:3000/all')
		.then((res) => res.json())
		.then((posts) => {
			setPosts(posts);
		});
	}, []);

	return (
		<div className="HomePage">
			<Header />
			<Routes>
				<Route exact path='/' element={	<HomePostList posts={posts} /> }></Route>
				<Route exact path='/users' element= { <UserPage />}> </Route>
				<Route exact path='/profile' element={ <Profile />}></Route>
				<Route exact path='/post' element={ <PostPage /> }></Route>
				<Route exact path='/create' element={ <CreatePostForm /> }></Route>
				<Route exact path='/editProfile' element={<EditProfileForm /> }></Route>
				<Route exact path='/editPost/:id' element={<PostUpdateForm />}></Route>
				<Route exact path='/dashboard/' element={<Dashboard />}></Route>
			</Routes>
		</div>
	)
}
