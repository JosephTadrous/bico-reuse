import React from 'react';
import { useState, useEffect } from 'react';
import './HomePost.css';
import HomePostList from './HomePostList';
import { NavLink, Routes, Route } from 'react-router-dom';

export default function Home() {
	const [posts, setPosts] = useState([]);

	useEffect( () => {
		fetch('http://localhost:3000/api')
		.then((res) => res.json())
		.then((posts) => {
			setPosts(posts);
		});
	}, []);

	return (

		<Routes>
			<Route exact path='/' element={	<HomePostList posts={posts} /> }></Route>
		</Routes>
	)
}
