import React from 'react';
import { NavLink, useParams, useLocation} from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import './PostUpdateForm.css'

export default function PostUpdateForm() {

    const {id} = useParams();
    const location= useLocation();
	const { state }= location;
	const post = state.post;

    const navigate = useNavigate();
    const clickHandler = () => {
        navigate("/post", {state:{pid: id}});
    }

    // const [post, setPost] = useState(null);

	// useEffect( () => {
	// 	fetch('http://localhost:3000/post?pid=' + id)
	// 	.then((res) => res.json())
	// 	.then((post) => {
	// 		setPost(post);
	// 	});
	// });

	return (

		<div className="EditForm">
			<h1>Edit Post</h1>
			<form className="InputForm" action="http://localhost:3000/edit_post/" method="post">
					<input type="hidden" name="id" defaultValue={post._id} />
					<div className="TitleInput"><div className="TextContainer">Title:</div> <input name="title" defaultValue={post.title}/></div>  
					<div className="DescriptionInput"><div className="TextContainer">Description:</div> <input name="description" defaultValue={post.description}/></div> 
					<div className="PriceInput"><div className="TextContainer">Price:</div> <input name="price" defaultValue={post.price}/></div> 
					<div className="PhotoInput"><div className="TextContainer">Image:</div> <input name="photo" defaultValue={post.image}/></div> 
					<div className="StatusInput"><div className="TextContainer">Status:</div> <input name="status" defaultValue={post.status}/></div>	

					<div className="Buttons">
						<button onClick={clickHandler} className="CancelButton" type="submit">Cancel</button>
						<button className="EditButton" type="submit">Submit</button>
					</div>
			</form>
    </div>
	)
}


