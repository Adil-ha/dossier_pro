import React from 'react';
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';


const ArticleItem = ({ article }) => {
    const navigate = useNavigate();

    const handleDetailsArticle = (articleId) => {
        navigate("/articleDetails/" + articleId);
    };

    return (
        <div className="col-md-4 mb-4">
            <div className="card h-100" >
                <img src={`/images/${article.image}`} className="card-img-top" alt={article.title} />
                <div className="card-body">
                    <h5 className="card-title">{article.title}</h5>
                    <p className="card-text">{article.content.substring(0, 100)}...</p>
                    <p className="card-text"><small className="text-muted">By {article.user.name} on {new Date(article.createdAt).toLocaleDateString()}</small></p>
                    <button onClick={() => handleDetailsArticle(article.id)} className="btn btn-primary">Read More</button>
                </div>
            </div>
        </div>
    );
};

export default ArticleItem;
