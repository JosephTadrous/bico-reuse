import React from 'react';
import {useState, useEffect} from 'react';
import Post from './Post';
import { Route, Routes, useLocation } from 'react-router-dom';
import './Post.css';
import { NavLink } from 'react-router-dom';
import PostUpdateForm from './PostUpdateForm';


export default function PostPage() {
	let [post, setPost]= useState(null);

	const location= useLocation();
	const { state }= location;
	const pid= state.pid;

	// only fetches when the location changes
	useEffect(() => {
		fetch('http://localhost:3000/post?pid=' + pid)
		.then((res) => res.json())
		.then((post) => {
			setPost(post);
		})}, [location]);

	return (
		<div className="PostPage">
			<Post post={post} />

			<Routes>
				<Route exact path='/editPost' element={ <PostUpdateForm post={post}/>}></Route>
			</Routes>

			<div className="EditButton">
				<NavLink className="CreateEditButton" to={'/editPost'}>
					<h3>Edit</h3>
				</NavLink>	
			</div>
	
		</div>
	)
}
