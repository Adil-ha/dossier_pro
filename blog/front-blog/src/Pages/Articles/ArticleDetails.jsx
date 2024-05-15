import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { fetchArticleById } from "../../features/ArticleSlice";
import { createComment, deleteComment } from "../../features/CommentSlice";
import { accountService } from '../../Services/accountServices';

const ArticleDetails = () => {
    const { id } = useParams();
    const dispatch = useDispatch();
    const article = useSelector((state) => state.articles.article);
    const loading = useSelector((state) => state.articles.loading);
    const commentContentRef = useRef(null);
    const [comments, setComments] = useState([]);

    useEffect(() => {
        dispatch(fetchArticleById(id));
    }, [dispatch, id]);

    useEffect(() => {
        if (article) {
            setComments(article.comments);
        }
    }, [article]);

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        const userId = accountService.getCurrentUser().id;
        const articleId = article.id;
        const content = commentContentRef.current.value;

        const newComment = {
            userId: userId,
            articleId: articleId,
            content: content
        };

        if (!content) {
            alert("Attention votre commentaire est vide !");
            return;
        }

        const resultAction = await dispatch(createComment(newComment));
        if (createComment.fulfilled.match(resultAction)) {
            setComments([...comments, resultAction.payload]);
            commentContentRef.current.value = "";
        } else {
            alert("Failed to add comment. Please try again.");
        }
    };

    const handleDeleteComment = async (commentId) => {
        const resultAction = await dispatch(deleteComment({commentId}));
        if (deleteComment.fulfilled.match(resultAction)) {
            setComments(comments.filter(comment => comment.id !== commentId));
        } else {
            alert("Failed to delete comment. Please try again.");
        }
    };

    if (loading) {
        return (
            <div className="text-center mt-5">
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }

    if (!article) {
        return <div className="alert alert-danger" role="alert">Article not found</div>;
    }

    return (
        <div className="container mt-4">
            <div className="card border-primary">
                <div className="row g-0">
                    <div className="col-md-6">
                        <img src={`/images/${article.image}`} className="img-fluid w-100 h-100" alt={article.title} />
                    </div>
                    <div className="col-md-6">
                        <div className="card-body">
                            <h5 className="card-title text-primary">{article.title}</h5>
                            <p className="card-text">By: {article.user.name}</p>
                            <p className="card-text">{article.content}</p>
                            <p className="card-text">
                                <small className="text-muted">Created at: {new Date(article.createdAt).toLocaleDateString()}</small>
                            </p>
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    Category: <span className="badge bg-primary">{article.categories[0]?.name}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div className="card border-primary mt-4">
                <div className="card-body">
                    <h5 className="card-title text-primary">Comments</h5>
                    {comments.map(comment => (
                        <div key={comment.id} className="border rounded p-2 my-2 bg-light">
                            <p>{comment.content}</p>
                            <p>
                                <small className="text-muted">Comment by: {comment.user.name} on {new Date(comment.createdAt).toLocaleDateString()}</small>
                            </p>
                            {accountService.isLogged() && accountService.getCurrentUser().id === comment.user.id && (
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => handleDeleteComment(comment.id)}
                                >
                                    Delete
                                </button>
                            )}
                        </div>
                    ))}
                </div>
                <div className="card-body">
                    {accountService.isLogged() ? (
                        <form onSubmit={handleCommentSubmit}>
                            <div className="mb-3">
                                <label htmlFor="content" className="form-label">Your Comment</label>
                                <textarea
                                    className="form-control"
                                    id="content"
                                    rows="3"
                                    ref={commentContentRef} 
                                ></textarea>
                            </div>
                            <button type="submit" className="btn btn-primary">Submit</button>
                        </form>
                    ) : (
                        <div className="alert alert-info" role="alert">
                            Please login to add a comment
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ArticleDetails;


