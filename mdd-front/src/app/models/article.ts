import { Topic } from './topic';
import { Comment } from './comment';

export class Article {
  id!: number;
  title!: string;
  description!: string;
  date!: string;
  user!: UserInfo;
  topic!: Topic;
  comments!: [];
}

export class UserInfo {
  id!: number;
  username!: string;
}

export class ArticleRequest {
  title!: string;
  description!: string;
  topicId!: number;
}

export class ArticleResponse {
  id!: number;
  title!: string;
  description!: string;
  userId!: number;
  topicId!: number;
}

export class ArticleDetail {
  id!: number;
  title!: string;
  description!: string;
  date!: string;
  user!: UserInfo;
  topic!: Topic;
  comments!: Comment[];
}
