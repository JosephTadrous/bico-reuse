import React from 'react';
import { NavLink } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

export default function PostUpdateForm({post}) {
    const navigate = useNavigate();
    const clickHandler = () => {
        navigate(-1, {replace: true});
    }

    var urllink = "http://localhost:3000/edit_post?id=" + post._id.toString();

	return (

		<html> 
        <form action="http://localhost:3000/edit_post" method="post">
            Title: <input name="title"/>  <p> </p>
            Description: <input name="description"/> <p> </p>
            Price: <input name="price"/> <p> </p>
            Image: <input type="image" name="image"/> <p> </p>
            Status: <input name="status"/> <p> </p>
            <button onClick={clickHandler} type="submit">Cancel</button>	 
            <button onClick={clickHandler} type="submit">Submit</button>	

            {/* <button onClick={() => { navigate(-1) }} type="submit">Cancel</button>	
            <button onClick={() => { navigate(-1) }} type="submit">Submit</button>	 */} 

            

            {/* <button style={{ width: "70px", height: "50px"}} type="submit" value="Cancel"/>
            <button style={{ width: "70px", height: "50px"}} type="submit" value="Submit"/> */}
{/* 
            <div className="Submission">
                <NavLink to={'/post'} state={{pid: post._id}}>
                    <h3>Submit</h3>
                </NavLink>	 
            </div> */}
        </form>
        </html>


	)
}


