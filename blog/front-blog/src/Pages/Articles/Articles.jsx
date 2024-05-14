import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAllArticles } from "../../features/ArticleSlice";
import 'bootstrap/dist/css/bootstrap.min.css';
import ArticleItem from '../../Components/ArticleItem';

const Articles = () => {
    const dispatch = useDispatch();
    const articles = useSelector((state) => state.articles.articles);
    const loading = useSelector((state) => state.articles.loading);
    const error = useSelector((state) => state.articles.error);

    useEffect(() => {
        dispatch(fetchAllArticles());
    }, [dispatch]);

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-5">Animals Blog</h1>
            {loading && <p className="text-center">Loading...</p>}
            {error && <p className="text-center text-danger">{error}</p>}
            <div className="row">
                {articles.map((article) => (
                    <ArticleItem key={article.id} article={article} />
                ))}
            </div>
        </div>
    );
};

export default Articles;
