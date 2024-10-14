import { UserInfo } from './article';

export class Comment {
  content!: string;
  user!: UserInfo;
  date!: string;
}

export class CommentRequest {
  content!: string;
}
