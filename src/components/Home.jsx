import React from 'react';
import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';

import Header from './Header';
import './Home.css';
import HomePostList from './HomePostList';
import PostPage from './PostPage';
import CreatePostForm from './CreatePostForm';

export default function Home() {
	const [posts, setPosts] = useState([]);

	useEffect( () => {
		fetch('http://localhost:3000/api')
		.then((res) => res.json())
		.then((posts) => {
			setPosts(posts);
		});
	});

	return (
		<div className="HomePage">
			<Header />
			<Routes>
				<Route exact path='/' element={	<HomePostList posts={posts} /> }></Route>
				<Route exact path='/profile' element={ <h1>Placeholder</h1> }></Route>
				<Route exact path='/post' element={ <PostPage /> }></Route>
				<Route exact path='/create' element={ <CreatePostForm /> }></Route>
				<Route exact path='/editProfile' element={ <h1>Placeholder</h1> }></Route>
				<Route exact path='/editPost' element={ <PostPage /> }></Route>
				
			</Routes>
		</div>
	)
}
