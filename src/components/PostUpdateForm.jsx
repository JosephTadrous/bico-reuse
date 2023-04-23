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
        <form action="http://localhost:3000/edit_post/" method="post">
            <input type="hidden" name="id" defaultValue={post._id}/> <p> </p>
            Title: <input name="title" defaultValue={post.title} />  <p> </p>
            Description: <input name="description" defaultValue={post.description}/> <p> </p>
            Price: <input name="price" defaultValue={post.price}/> <p> </p>
            Image: <input name="image" defaultValue={post.photos}/> <p> </p>
            Status: <input name="status" defaultValue={post.status}/> <p> </p>
            {/* <button onClick={clickHandler} type="submit">Cancel</button>	 
            <button onClick={clickHandler} type="submit">Submit</button>	 */}
						<button onClick={clickHandler} className="EditButton" type="submit">Cancel</button>	 
            <button className="EditButton" type="submit">Submit</button>	
        </form>
        </div>

	)
}


